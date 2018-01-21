import { BaseEntity } from './../../shared';

export const enum LegalStatus {
    'SARL',
    'SA',
    'SAS',
    'SASU',
    'SNC',
    'EURL',
    'EI',
    'ASSOCIATION',
    'AUTO_ENTREPRENEUR'
}

export class Agency implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public siret?: string,
        public siren?: string,
        public legalStatus?: LegalStatus,
        public logoContentType?: string,
        public logo?: any,
        public users?: BaseEntity[],
        public contacts?: BaseEntity[],
        public attachements?: BaseEntity[],
    ) {
    }
}
