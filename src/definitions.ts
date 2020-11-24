declare module '@capacitor/core' {
  interface PluginRegistry {
    Ecies: EciesPlugin;
  }
}

export interface EciesPlugin {
  generateKeys(): Promise<{ publicKey: string, privateKey: string }>;
  encrypt(options: { publickey: string, plaintext: string }): Promise<{ value: string }>;
  decrypt(options: { privatekey: string, crypttext: string }): Promise<{ value: string }>;
}
