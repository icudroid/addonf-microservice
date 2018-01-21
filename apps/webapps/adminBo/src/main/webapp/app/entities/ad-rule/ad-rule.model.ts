import { BaseEntity } from './../../shared';

export class AdRule implements BaseEntity {
    constructor(
        public id?: number,
        public adId?: number,
    ) {
    }
}
