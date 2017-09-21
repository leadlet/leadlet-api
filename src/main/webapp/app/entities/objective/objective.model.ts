import { BaseEntity } from './../../shared';

const enum ObjectiveSourceType {
    'USER',
    'TEAM'
}

export class Objective implements BaseEntity {
    constructor(
        public id?: number,
        public sourceId?: number,
        public sourceType?: ObjectiveSourceType,
        public name?: string,
        public dueDate?: any,
        public targetAmount?: number,
        public currentAmount?: number,
    ) {
    }
}
