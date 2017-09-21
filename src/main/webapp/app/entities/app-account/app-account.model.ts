import { BaseEntity } from './../../shared';

export class AppAccount implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public address?: string,
        public subscriptionPlans?: BaseEntity[],
        public users?: BaseEntity[],
    ) {
    }
}
