import { registerPlugin } from '@capacitor/core';
const Ecies = registerPlugin('Ecies', {
    web: () => import('./web').then(m => new m.EciesWeb()),
});
export * from './definitions';
export { Ecies };
//# sourceMappingURL=index.js.map