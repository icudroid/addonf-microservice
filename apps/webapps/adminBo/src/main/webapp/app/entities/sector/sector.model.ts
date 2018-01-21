import { BaseEntity } from './../../shared';

export class Sector implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public description?: string,
    ) {
    }
}
