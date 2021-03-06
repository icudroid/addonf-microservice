import { BaseEntity } from './../../shared';

export const enum Sex {
    'MR',
    'MME'
}

export const enum AgeGroup {
    'FROM_0_TO_9',
    'FROM_10_TO_14',
    'FROM_14_TO_17',
    'FROM_18_TO_24',
    'FROM_25_TO_34',
    'FROM_35_TO_49',
    'FROM_50_TO_64',
    'MORE_THAN_65'
}

export class CustomerTarget implements BaseEntity {
    constructor(
        public id?: number,
        public sex?: Sex,
        public age?: AgeGroup,
        public brandId?: number,
    ) {
    }
}
