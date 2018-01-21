/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdvertiserBoTestModule } from '../../../test.module';
import { MeanOfContactComponent } from '../../../../../../main/webapp/app/entities/mean-of-contact/mean-of-contact.component';
import { MeanOfContactService } from '../../../../../../main/webapp/app/entities/mean-of-contact/mean-of-contact.service';
import { MeanOfContact } from '../../../../../../main/webapp/app/entities/mean-of-contact/mean-of-contact.model';

describe('Component Tests', () => {

    describe('MeanOfContact Management Component', () => {
        let comp: MeanOfContactComponent;
        let fixture: ComponentFixture<MeanOfContactComponent>;
        let service: MeanOfContactService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [MeanOfContactComponent],
                providers: [
                    MeanOfContactService
                ]
            })
            .overrideTemplate(MeanOfContactComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MeanOfContactComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MeanOfContactService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new MeanOfContact(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.meanOfContacts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
