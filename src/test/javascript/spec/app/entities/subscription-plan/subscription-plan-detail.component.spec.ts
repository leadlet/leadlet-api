/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LeadletApiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SubscriptionPlanDetailComponent } from '../../../../../../main/webapp/app/entities/subscription-plan/subscription-plan-detail.component';
import { SubscriptionPlanService } from '../../../../../../main/webapp/app/entities/subscription-plan/subscription-plan.service';
import { SubscriptionPlan } from '../../../../../../main/webapp/app/entities/subscription-plan/subscription-plan.model';

describe('Component Tests', () => {

    describe('SubscriptionPlan Management Detail Component', () => {
        let comp: SubscriptionPlanDetailComponent;
        let fixture: ComponentFixture<SubscriptionPlanDetailComponent>;
        let service: SubscriptionPlanService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LeadletApiTestModule],
                declarations: [SubscriptionPlanDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SubscriptionPlanService,
                    JhiEventManager
                ]
            }).overrideTemplate(SubscriptionPlanDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SubscriptionPlanDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubscriptionPlanService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SubscriptionPlan(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.subscriptionPlan).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
