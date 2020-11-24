import { WebPlugin } from '@capacitor/core';
import { EciesPlugin } from './definitions';

export class EciesWeb extends WebPlugin implements EciesPlugin {
  constructor() {
    super({
      name: 'Ecies',
      platforms: ['web'],
    });
  }

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

const Ecies = new EciesWeb();

export { Ecies };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(Ecies);
