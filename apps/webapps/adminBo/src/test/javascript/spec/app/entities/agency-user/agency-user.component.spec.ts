/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdminBoTestModule } from '../../../test.module';
import { AgencyUserComponent } from '../../../../../../main/webapp/app/entities/agency-user/agency-user.component';
import { AgencyUserService } from '../../../../../../main/webapp/app/entities/agency-user/agency-user.service';
import { AgencyUser } from '../../../../../../main/webapp/app/entities/agency-user/agency-user.model';

describe('Component Tests', () => {

    describe('AgencyUser Management Component', () => {
        let comp: AgencyUserComponent;
        let fixture: ComponentFixture<AgencyUserComponent>;
        let service: AgencyUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [AgencyUserComponent],
                providers: [
                    AgencyUserService
                ]
            })
            .overrideTemplate(AgencyUserComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgencyUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgencyUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new AgencyUser(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.agencyUsers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
