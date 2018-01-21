import { BaseEntity } from './../../shared';

export class MediaUser implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public mediaId?: number,
    ) {
    }
}
