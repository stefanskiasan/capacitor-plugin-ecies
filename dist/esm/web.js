import { WebPlugin } from '@capacitor/core';
export class EciesWeb extends WebPlugin {
    async generateKeys() {
        return new Promise((res) => {
            res({
                publicKey: '',
                privateKey: ''
            });
        });
    }
    async encrypt(options) {
        console.log(options);
        return new Promise((res) => {
            res({
                value: ''
            });
        });
    }
    async decrypt(options) {
        console.log(options);
        return new Promise((res) => {
            res({
                value: ''
            });
        });
    }
}
//# sourceMappingURL=web.js.map