/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LeadletApiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CompanySubscriptionPlanDetailComponent } from '../../../../../../main/webapp/app/entities/company-subscription-plan/company-subscription-plan-detail.component';
import { CompanySubscriptionPlanService } from '../../../../../../main/webapp/app/entities/company-subscription-plan/company-subscription-plan.service';
import { CompanySubscriptionPlan } from '../../../../../../main/webapp/app/entities/company-subscription-plan/company-subscription-plan.model';

describe('Component Tests', () => {

    describe('CompanySubscriptionPlan Management Detail Component', () => {
        let comp: CompanySubscriptionPlanDetailComponent;
        let fixture: ComponentFixture<CompanySubscriptionPlanDetailComponent>;
        let service: CompanySubscriptionPlanService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LeadletApiTestModule],
                declarations: [CompanySubscriptionPlanDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CompanySubscriptionPlanService,
                    JhiEventManager
                ]
            }).overrideTemplate(CompanySubscriptionPlanDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanySubscriptionPlanDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanySubscriptionPlanService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CompanySubscriptionPlan(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.companySubscriptionPlan).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
