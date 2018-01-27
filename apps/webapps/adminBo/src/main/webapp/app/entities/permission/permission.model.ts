import { BaseEntity } from './../../shared';

export class Permission implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public extention?: string,
        public description?: string,
    ) {
    }
}
