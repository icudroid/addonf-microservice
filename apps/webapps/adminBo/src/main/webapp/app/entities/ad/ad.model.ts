import { BaseEntity } from './../../shared';

export const enum AdStatus {
    'UPLOADING',
    'UPLOADED',
    'ENCODED',
    'DELETED',
    'READY_TO_USE'
}

export class Ad implements BaseEntity {
    constructor(
        public id?: number,
        public duration?: number,
        public status?: AdStatus,
        public adfileId?: number,
        public adCampaingId?: number,
        public bids?: BaseEntity[],
        public rules?: BaseEntity[],
    ) {
    }
}
