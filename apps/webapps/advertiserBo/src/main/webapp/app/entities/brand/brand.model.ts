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

export class Brand implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public userId?: number,
        public siret?: string,
        public siren?: string,
        public legalStatus?: LegalStatus,
        public logoContentType?: string,
        public logo?: any,
        public products?: BaseEntity[],
        public targets?: BaseEntity[],
        public targetsMedias?: BaseEntity[],
        public users?: BaseEntity[],
        public contacts?: BaseEntity[],
        public attachements?: BaseEntity[],
        public sectorId?: number,
    ) {
    }
}
