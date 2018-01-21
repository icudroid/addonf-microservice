import { BaseEntity } from './../../shared';

export const enum MeanOfContactType {
    'EMAIL',
    'ADDRESS',
    'PHONE'
}

export class MeanOfContact implements BaseEntity {
    constructor(
        public id?: number,
        public value?: string,
        public type?: MeanOfContactType,
        public contactId?: number,
    ) {
    }
}
