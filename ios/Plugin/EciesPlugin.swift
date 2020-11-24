import Foundation
import Capacitor
import CryptorECC

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(EciesPlugin)
public class EciesPlugin: CAPPlugin {

    @objc func generateKeys(_ call: CAPPluginCall) {
        do {
         let p256PrivateKey = try ECPrivateKey.make(for: .prime256v1)
         let eccPublicKey = try p256PrivateKey.extractPublicKey()
            call.success([
                "publicKey": eccPublicKey.pemString,
                "privateKey": p256PrivateKey.pemString
              ])
        } catch {
          print("\(error)")
        }    
    }

    @objc func encrypt(_ call: CAPPluginCall) {
        let pemPublic163k1 = call.getString("publickey") ?? ""
        let plaintext      = call.getString("plaintext") ?? ""
        var encryptedData  = ""

        do {
             encryptedData = try plaintext.encrypt(with: ECPublicKey(der: Data(base64Encoded: pemPublic163k1)!)).base64EncodedString();
        } catch {
          print("\(error)")
        }
    
        call.success([
            "value": encryptedData
        ])
    }

    @objc func decrypt(_ call: CAPPluginCall) {

        let pemPrivate163k1 = call.getString("privatekey") ?? ""
        let crypttext       = call.getString("crypttext") ?? ""
        var decryptedData:String?   = ""
    
        do {
            var key = try ECPrivateKey(key: pemPrivate163k1);
            var tempdecryptedData = try Data(base64Encoded: crypttext)!.decrypt(with: key )
            decryptedData = String(data: tempdecryptedData, encoding: .utf8)
        } catch {
          print("\(error)")
        }
        
        call.success([
            "value": decryptedData
        ])
    }
}
