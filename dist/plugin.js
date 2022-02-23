var capacitorEcies = (function (exports, core) {
    'use strict';

    const Ecies = core.registerPlugin('Ecies', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.EciesWeb()),
    });

    class EciesWeb extends core.WebPlugin {
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

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        EciesWeb: EciesWeb
    });

    exports.Ecies = Ecies;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
