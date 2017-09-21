/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LeadletApiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PipelineStageDetailComponent } from '../../../../../../main/webapp/app/entities/pipeline-stage/pipeline-stage-detail.component';
import { PipelineStageService } from '../../../../../../main/webapp/app/entities/pipeline-stage/pipeline-stage.service';
import { PipelineStage } from '../../../../../../main/webapp/app/entities/pipeline-stage/pipeline-stage.model';

describe('Component Tests', () => {

    describe('PipelineStage Management Detail Component', () => {
        let comp: PipelineStageDetailComponent;
        let fixture: ComponentFixture<PipelineStageDetailComponent>;
        let service: PipelineStageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LeadletApiTestModule],
                declarations: [PipelineStageDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PipelineStageService,
                    JhiEventManager
                ]
            }).overrideTemplate(PipelineStageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PipelineStageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PipelineStageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PipelineStage(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pipelineStage).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
