import { BaseEntity } from './../../shared';

export const enum AdMediaType {
    'WEB',
    'MOBILE',
    'RADIO',
    'TV',
    'STREAMING_VIDEO',
    'STREAMING_AUDIO'
}

export class BidCategoryMedia implements BaseEntity {
    constructor(
        public id?: number,
        public bid?: number,
        public mediaType?: AdMediaType,
        public adId?: number,
        public categoryId?: number,
        public mediaId?: number,
    ) {
    }
}
