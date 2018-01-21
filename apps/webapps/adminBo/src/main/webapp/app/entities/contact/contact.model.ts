import { BaseEntity } from './../../shared';

export class Contact implements BaseEntity {
    constructor(
        public id?: number,
        public lastname?: string,
        public firstname?: string,
        public brandId?: number,
        public agencyId?: number,
        public mediaId?: number,
        public meanOfContacts?: BaseEntity[],
    ) {
    }
}
