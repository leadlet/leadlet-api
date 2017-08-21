import { BaseEntity } from './../../shared';

const enum PhoneType {
    'HOME',
    'WORK',
    'OTHER'
}

export class ContactPhone implements BaseEntity {
    constructor(
        public id?: number,
        public phone?: string,
        public type?: PhoneType,
        public contactId?: number,
    ) {
    }
}
