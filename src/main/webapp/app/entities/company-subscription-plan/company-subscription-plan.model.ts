import { BaseEntity } from './../../shared';

export class CompanySubscriptionPlan implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: any,
        public endDate?: any,
        public appAccountId?: number,
        public subscriptionPlanId?: number,
    ) {
    }
}
