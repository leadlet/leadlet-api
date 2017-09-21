import { BaseEntity } from './../../shared';

export class Deal implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public order?: number,
        public potentialValue?: number,
        public pipelineStageId?: number,
        public personId?: number,
        public organizationId?: number,
        public appAccountId?: number,
        public userId?: number,
    ) {
    }
}
