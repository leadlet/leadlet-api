/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LeadletApiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AppAccountDetailComponent } from '../../../../../../main/webapp/app/entities/app-account/app-account-detail.component';
import { AppAccountService } from '../../../../../../main/webapp/app/entities/app-account/app-account.service';
import { AppAccount } from '../../../../../../main/webapp/app/entities/app-account/app-account.model';

describe('Component Tests', () => {

    describe('AppAccount Management Detail Component', () => {
        let comp: AppAccountDetailComponent;
        let fixture: ComponentFixture<AppAccountDetailComponent>;
        let service: AppAccountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LeadletApiTestModule],
                declarations: [AppAccountDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AppAccountService,
                    JhiEventManager
                ]
            }).overrideTemplate(AppAccountDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AppAccountDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppAccountService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AppAccount(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.appAccount).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
