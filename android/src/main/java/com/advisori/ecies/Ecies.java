package com.advisori.ecies;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IESParameterSpec;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.ECGenParameterSpec;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import com.advisori.ecies.ecies.*;



@NativePlugin
public class Ecies extends Plugin {
    public static byte[] iv = new SecureRandom().generateSeed(16);

    @PluginMethod
    public void generateKeys(PluginCall call) throws NoSuchAlgorithmException, InvalidKeyException, KeyStoreException, CertificateException, IOException, UnrecoverableKeyException, InvalidAlgorithmParameterException, NoSuchProviderException {


        SecureRandom random = new SecureRandom();

        Security.removeProvider("BC");
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC, "BC");
        ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("prime256v1");
        keyPairGenerator.initialize(ecGenParameterSpec, random);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        StringBuilder sbPublic = new StringBuilder();
        sbPublic.append("-----BEGIN PUBLIC KEY-----\n");
        sbPublic.append(Base64.encodeToString(keyPair.getPublic().getEncoded(),0));
        sbPublic.append("-----END PUBLIC KEY-----");


        JSObject ret = new JSObject();
        ret.put("publicKey",sbPublic);
        ret.put("privateKey",Base64.encodeToString(keyPair.getPrivate().getEncoded(),0));
        call.success(ret);
    }


    @PluginMethod
    public void decrypt(PluginCall call) throws Exception {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        String privatekey = call.getString("privatekey");
        String crypttext = call.getString("crypttext");

        byte[] privateKeys = Base64.decode(privatekey,0);

        String ts = decryptString(crypttext, privateKeys);

        JSObject ret = new JSObject();
        ret.put("value", ts);
        call.success(ret);
    }

    @PluginMethod
    public void encrypt(PluginCall call){

        JSObject ret = new JSObject();
        ret.put("value", "");
        call.success(ret);
    }


    public static String decryptString(String ciphertext, byte[] privateKeyBytes) throws Exception {
        java.security.spec.PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("EC", new BouncyCastleProvider());
        org.bouncycastle.jce.interfaces.ECPrivateKey privateKey = (ECPrivateKey) keyFactory.generatePrivate(encodedKeySpec);

        byte[] inputBytes = Base64.decode(ciphertext,0);

        IESParameterSpec params = new IESParameterSpec(null, null, 128, 128, null);
        IESCipherGCM cipher = new IESCipherGCM(
                new IESEngineGCM(
                        new ECDHBasicAgreement(),
                        new KDF2BytesGenerator(new SHA256Digest()),
                        new AESGCMBlockCipher()), 16);

        cipher.engineInit(Cipher.DECRYPT_MODE, privateKey, params, new SecureRandom());

        byte[] cipherResult = cipher.engineDoFinal(inputBytes, 0, inputBytes.length);
        return new String(cipherResult);

    }
}
