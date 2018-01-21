/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdvertiserBoTestModule } from '../../../test.module';
import { AgencyDetailComponent } from '../../../../../../main/webapp/app/entities/agency/agency-detail.component';
import { AgencyService } from '../../../../../../main/webapp/app/entities/agency/agency.service';
import { Agency } from '../../../../../../main/webapp/app/entities/agency/agency.model';

describe('Component Tests', () => {

    describe('Agency Management Detail Component', () => {
        let comp: AgencyDetailComponent;
        let fixture: ComponentFixture<AgencyDetailComponent>;
        let service: AgencyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [AgencyDetailComponent],
                providers: [
                    AgencyService
                ]
            })
            .overrideTemplate(AgencyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgencyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgencyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Agency(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.agency).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
