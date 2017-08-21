import { BaseEntity } from './../../shared';

export class Document implements BaseEntity {
    constructor(
        public id?: number,
        public url?: string,
        public memo?: string,
        public contactId?: number,
        public appAccountId?: number,
        public activityId?: number,
    ) {
    }
}
