import { BaseEntity } from './../../shared';

export class BrandUser implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public brandId?: number,
    ) {
    }
}
