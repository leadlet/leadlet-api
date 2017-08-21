import { BaseEntity } from './../../shared';

export class Stage implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public pipelineStages?: BaseEntity[],
    ) {
    }
}
