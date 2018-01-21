/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdminBoTestModule } from '../../../test.module';
import { MeanOfContactDetailComponent } from '../../../../../../main/webapp/app/entities/mean-of-contact/mean-of-contact-detail.component';
import { MeanOfContactService } from '../../../../../../main/webapp/app/entities/mean-of-contact/mean-of-contact.service';
import { MeanOfContact } from '../../../../../../main/webapp/app/entities/mean-of-contact/mean-of-contact.model';

describe('Component Tests', () => {

    describe('MeanOfContact Management Detail Component', () => {
        let comp: MeanOfContactDetailComponent;
        let fixture: ComponentFixture<MeanOfContactDetailComponent>;
        let service: MeanOfContactService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [MeanOfContactDetailComponent],
                providers: [
                    MeanOfContactService
                ]
            })
            .overrideTemplate(MeanOfContactDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MeanOfContactDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MeanOfContactService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new MeanOfContact(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.meanOfContact).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
