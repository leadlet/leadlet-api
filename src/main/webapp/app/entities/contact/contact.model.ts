import { BaseEntity } from './../../shared';

const enum ContactType {
    'PERSON',
    'ORGANIZATION'
}

export class Contact implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public location?: string,
        public type?: ContactType,
        public isContactPerson?: boolean,
        public phones?: BaseEntity[],
        public emails?: BaseEntity[],
        public documents?: BaseEntity[],
        public organizationId?: number,
    ) {
        this.isContactPerson = false;
    }
}
