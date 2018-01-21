import { BaseEntity } from './../../shared';

export class City implements BaseEntity {
    constructor(
        public id?: number,
        public zipcode?: string,
        public name?: string,
        public lon?: number,
        public lat?: number,
        public countryId?: number,
    ) {
    }
}
