import { BaseEntity } from './../../shared';

export const enum AdMediaType {
    'WEB',
    'MOBILE',
    'RADIO',
    'TV',
    'STREAMING_VIDEO',
    'STREAMING_AUDIO'
}

export class MediaTarget implements BaseEntity {
    constructor(
        public id?: number,
        public mediaType?: AdMediaType,
        public brandId?: number,
    ) {
    }
}
