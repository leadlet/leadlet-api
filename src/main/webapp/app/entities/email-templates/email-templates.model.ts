import { BaseEntity } from './../../shared';

export class EmailTemplates implements BaseEntity {
    constructor(
        public id?: number,
        public templateName?: string,
        public template?: string,
    ) {
    }
}
