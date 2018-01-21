import { BaseEntity } from './../../shared';

export class AgencyUser implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public agencyId?: number,
    ) {
    }
}
