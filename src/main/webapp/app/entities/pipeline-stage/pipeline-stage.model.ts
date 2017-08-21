import { BaseEntity } from './../../shared';

export class PipelineStage implements BaseEntity {
    constructor(
        public id?: number,
        public stageOrder?: number,
        public pipelineId?: number,
        public stageId?: number,
    ) {
    }
}
