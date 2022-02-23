import { WebPlugin } from '@capacitor/core';

import type { EciesPlugin } from './definitions';

export class EciesWeb extends WebPlugin implements EciesPlugin {
  async generateKeys(): Promise<{ publicKey: string, privateKey: string }> {
    return new Promise((res) => {
      res({
        publicKey: '',
        privateKey: ''
      })
    });
  }
  async encrypt(options: any): Promise<{ value: string }> {
    console.log(options);
    return new Promise((res) => {
      res({
        value: ''
      })
    });
  }
  async decrypt(options: any): Promise<{ value: string }> {
    console.log(options);
    return new Promise((res) => {
      res({
        value: ''
      })
    });
  }
}
