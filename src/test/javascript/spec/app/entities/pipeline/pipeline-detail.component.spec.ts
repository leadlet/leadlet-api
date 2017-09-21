/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LeadletApiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PipelineDetailComponent } from '../../../../../../main/webapp/app/entities/pipeline/pipeline-detail.component';
import { PipelineService } from '../../../../../../main/webapp/app/entities/pipeline/pipeline.service';
import { Pipeline } from '../../../../../../main/webapp/app/entities/pipeline/pipeline.model';

describe('Component Tests', () => {

    describe('Pipeline Management Detail Component', () => {
        let comp: PipelineDetailComponent;
        let fixture: ComponentFixture<PipelineDetailComponent>;
        let service: PipelineService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LeadletApiTestModule],
                declarations: [PipelineDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PipelineService,
                    JhiEventManager
                ]
            }).overrideTemplate(PipelineDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PipelineDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PipelineService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Pipeline(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pipeline).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
