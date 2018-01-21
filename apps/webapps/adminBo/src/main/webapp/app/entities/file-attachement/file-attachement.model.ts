import { BaseEntity } from './../../shared';

export class FileAttachement implements BaseEntity {
    constructor(
        public id?: number,
        public fileContentType?: string,
        public file?: any,
        public brandId?: number,
        public agencyId?: number,
        public mediaId?: number,
    ) {
    }
}
