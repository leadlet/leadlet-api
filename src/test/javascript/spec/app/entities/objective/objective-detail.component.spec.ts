/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LeadletApiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ObjectiveDetailComponent } from '../../../../../../main/webapp/app/entities/objective/objective-detail.component';
import { ObjectiveService } from '../../../../../../main/webapp/app/entities/objective/objective.service';
import { Objective } from '../../../../../../main/webapp/app/entities/objective/objective.model';

describe('Component Tests', () => {

    describe('Objective Management Detail Component', () => {
        let comp: ObjectiveDetailComponent;
        let fixture: ComponentFixture<ObjectiveDetailComponent>;
        let service: ObjectiveService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LeadletApiTestModule],
                declarations: [ObjectiveDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ObjectiveService,
                    JhiEventManager
                ]
            }).overrideTemplate(ObjectiveDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ObjectiveDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObjectiveService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Objective(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.objective).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
