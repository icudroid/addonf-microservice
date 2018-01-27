import { BaseEntity } from './../../shared';

export class Profile implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public roles?: BaseEntity[],
    ) {
    }
}
