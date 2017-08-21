import { BaseEntity } from './../../shared';

const enum EmailType {
    'HOME',
    'WORK',
    'OTHER'
}

export class ContactEmail implements BaseEntity {
    constructor(
        public id?: number,
        public email?: string,
        public type?: EmailType,
        public contactId?: number,
    ) {
    }
}
