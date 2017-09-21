import { BaseEntity } from './../../shared';

const enum PlanName {
    'TRIAL',
    'BASIC',
    'ADVANCED'
}

export class SubscriptionPlan implements BaseEntity {
    constructor(
        public id?: number,
        public planName?: PlanName,
        public allowedFeatures?: string,
        public subscriptionPlans?: BaseEntity[],
    ) {
    }
}
