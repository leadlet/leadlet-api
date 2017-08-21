import { BaseEntity } from './../../shared';

export class Activity implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public order?: number,
        public memo?: string,
        public potentialValue?: number,
        public startDate?: any,
        public endDate?: any,
        public documents?: BaseEntity[],
        public dealId?: number,
        public personId?: number,
        public organizationId?: number,
        public appAccountId?: number,
        public userId?: number,
    ) {
    }
}
