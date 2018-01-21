import { BaseEntity } from './../../shared';

export class Product implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public publicPrice?: number,
        public adPrice?: number,
        public imageContentType?: string,
        public image?: any,
        public adCampaingId?: number,
        public brandId?: number,
    ) {
    }
}
