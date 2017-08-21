/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LeadletApiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AppUserDetailComponent } from '../../../../../../main/webapp/app/entities/app-user/app-user-detail.component';
import { AppUserService } from '../../../../../../main/webapp/app/entities/app-user/app-user.service';
import { AppUser } from '../../../../../../main/webapp/app/entities/app-user/app-user.model';

describe('Component Tests', () => {

    describe('AppUser Management Detail Component', () => {
        let comp: AppUserDetailComponent;
        let fixture: ComponentFixture<AppUserDetailComponent>;
        let service: AppUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LeadletApiTestModule],
                declarations: [AppUserDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AppUserService,
                    JhiEventManager
                ]
            }).overrideTemplate(AppUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AppUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AppUser(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.appUser).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
