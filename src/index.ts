import { registerPlugin } from '@capacitor/core';

import type { EciesPlugin } from './definitions';

const Ecies = registerPlugin<EciesPlugin>('Ecies', {
  web: () => import('./web').then(m => new m.EciesWeb()),
});

export * from './definitions';
export { Ecies };
