import { BaseEntity } from './../../shared';

export class Pipeline implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public order?: number,
        public pipelineStages?: BaseEntity[],
    ) {
    }
}
