/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LeadletApiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ContactPhoneDetailComponent } from '../../../../../../main/webapp/app/entities/contact-phone/contact-phone-detail.component';
import { ContactPhoneService } from '../../../../../../main/webapp/app/entities/contact-phone/contact-phone.service';
import { ContactPhone } from '../../../../../../main/webapp/app/entities/contact-phone/contact-phone.model';

describe('Component Tests', () => {

    describe('ContactPhone Management Detail Component', () => {
        let comp: ContactPhoneDetailComponent;
        let fixture: ComponentFixture<ContactPhoneDetailComponent>;
        let service: ContactPhoneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LeadletApiTestModule],
                declarations: [ContactPhoneDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ContactPhoneService,
                    JhiEventManager
                ]
            }).overrideTemplate(ContactPhoneDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContactPhoneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactPhoneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ContactPhone(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.contactPhone).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
