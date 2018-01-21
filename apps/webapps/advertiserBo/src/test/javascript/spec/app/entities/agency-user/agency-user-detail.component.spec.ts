/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdvertiserBoTestModule } from '../../../test.module';
import { AgencyUserDetailComponent } from '../../../../../../main/webapp/app/entities/agency-user/agency-user-detail.component';
import { AgencyUserService } from '../../../../../../main/webapp/app/entities/agency-user/agency-user.service';
import { AgencyUser } from '../../../../../../main/webapp/app/entities/agency-user/agency-user.model';

describe('Component Tests', () => {

    describe('AgencyUser Management Detail Component', () => {
        let comp: AgencyUserDetailComponent;
        let fixture: ComponentFixture<AgencyUserDetailComponent>;
        let service: AgencyUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [AgencyUserDetailComponent],
                providers: [
                    AgencyUserService
                ]
            })
            .overrideTemplate(AgencyUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgencyUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgencyUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new AgencyUser(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.agencyUser).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
