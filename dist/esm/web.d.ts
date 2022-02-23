import { WebPlugin } from '@capacitor/core';
import type { EciesPlugin } from './definitions';
export declare class EciesWeb extends WebPlugin implements EciesPlugin {
    generateKeys(): Promise<{
        publicKey: string;
        privateKey: string;
    }>;
    encrypt(options: any): Promise<{
        value: string;
    }>;
    decrypt(options: any): Promise<{
        value: string;
    }>;
}
