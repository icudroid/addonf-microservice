import { BaseEntity } from './../../shared';

export class AdCampaing implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public initialAmount?: number,
        public start?: any,
        public end?: any,
        public ads?: BaseEntity[],
        public products?: BaseEntity[],
        public brandId?: number,
        public sectorId?: number,
        public providedById?: number,
    ) {
    }
}
