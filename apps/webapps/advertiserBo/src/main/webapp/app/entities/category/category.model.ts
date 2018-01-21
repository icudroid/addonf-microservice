import { BaseEntity } from './../../shared';

export class Category implements BaseEntity {
    constructor(
        public id?: number,
        public key?: string,
        public description?: string,
        public mainId?: number,
    ) {
    }
}
